package my.hikandgo.xmlbuilderapp.service;

import my.hikandgo.xmlbuilderapp.constants.DateFormats;
import my.hikandgo.xmlbuilderapp.constants.Tegs407;
import my.hikandgo.xmlbuilderapp.models.Builder407model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Builder407 {

    public static void buildActualDate(ArrayList<Path> targetPathList, ArrayList<Path> secondPathList, Builder407model model) throws IOException {


        List<Path> xmlList = targetPathList.stream()
                .filter(x -> x.toString().endsWith("XML") & !(x.getFileName().toString().startsWith("VBK")))
                .collect(Collectors.toList());
        List<Path> xmlSecondList = secondPathList.stream()
                .filter(x -> x.toString().endsWith("XML") & !(x.getFileName().toString().startsWith("VBK")))
                .collect(Collectors.toList());
        List<Path> incList = targetPathList.stream()
                .filter(x -> !(x.toString().endsWith("XML")) || x.getFileName().toString().startsWith("VBK"))
                .collect(Collectors.toList());
        List<Path> incSecondList = secondPathList.stream()
                .filter(x -> !(x.toString().endsWith("XML")) || x.getFileName().toString().startsWith("VBK"))
                .collect(Collectors.toList());

        for (Path targetPath: xmlList) {
            try (
                    Stream<String> input = Files.lines(targetPath);
                    BufferedWriter output = new BufferedWriter(new FileWriter(xmlSecondList.get(xmlList.indexOf(targetPath)).toString()))
                    ) {
                String[] newList = input.toArray(String[]::new);
                for (String str:newList) {
                    if (str.contains(Tegs407.DATE_MSG.getTegOpen())) {
                        String newString = getNewStringDateLane(
                                str,
                                DateFormats.currentDateGenerator(DateFormats.MAIN_DATE_FORMAT, model.getDATE_MSG_DIFF()),
                                Tegs407.DATE_MSG.getTegOpen(),
                                Tegs407.DATE_MSG.getTegClose()
                        );
                        output.write(newString + "\n");
                    } else if (str.contains(Tegs407.DATE_REQUEST.getTegOpen())) {
                        String newString = getNewStringDateLane(
                                str,
                                DateFormats.currentDateGenerator(DateFormats.MAIN_DATE_FORMAT, model.getDATE_REQUEST_DIFF()),
                                Tegs407.DATE_REQUEST.getTegOpen(),
                                Tegs407.DATE_REQUEST.getTegClose()
                        );
                        output.write(newString + "\n");
                    } else if(str.contains(Tegs407.DATE_GET_REQ.getTegOpen())) {
                        String newString = getNewStringDateLane(
                                str,
                                DateFormats.currentDateGenerator(DateFormats.MAIN_DATE_FORMAT, model.getDATE_GET_REQ_DIFF()),
                                Tegs407.DATE_GET_REQ.getTegOpen(),
                                Tegs407.DATE_GET_REQ.getTegClose()
                        );
                        output.write(newString + "\n");
                    } else if (str.contains(Tegs407.DATE_ORDER.getTegOpen())) {
                        String newString = getNewStringDateLane(
                                str,
                                DateFormats.currentDateGenerator(DateFormats.MAIN_DATE_FORMAT, model.getDATE_ORDER_DIFF()),
                                Tegs407.DATE_ORDER.getTegOpen(),
                                Tegs407.DATE_ORDER.getTegClose()
                        );
                        output.write(newString + "\n");
                    } else if (str.contains(Tegs407.DATE_PROLONG.getTegOpen())) {
                        String newString = getNewStringDateLane(
                                str,
                                DateFormats.currentDateGenerator(DateFormats.MAIN_DATE_FORMAT, model.getDATE_PROLONG_DIFF()),
                                Tegs407.DATE_PROLONG.getTegOpen(),
                                Tegs407.DATE_PROLONG.getTegClose()
                        );
                        output.write(newString + "\n");
                    } else if (str.contains(Tegs407.INV_NAME.getTegOpen()) & !(str.contains("VBKO"))) {
                        String newString = getNewStringINC(str);
                        output.write(newString + "\n");
                    } else {
                        output.write(str + "\n");
                    }
                }
            }
        }

        moveRenameFiles(incList, incSecondList);

    }

    public static String getNewStringDateLane(String str, String newDateString, String tegOpen, String tegClose) {
        int numberSpace = str.length() - str.replaceAll(" ", "").length();

        return " ".repeat(numberSpace)
                .concat(tegOpen)
                .concat(newDateString)
                .concat(tegClose);
    }

    public static String getNewStringINC(String str) {
        int lastIndex = str.lastIndexOf("_");
        StringBuilder strBuild = new StringBuilder(str);
        strBuild.replace(
                (lastIndex - 8), lastIndex, DateFormats.currentDateGenerator(DateFormats.FILENAME_FORMAT, 0));
        return strBuild.toString();
    }

    public static void moveRenameFiles(List<Path> targetFiles, List<Path> secondFiles) throws IOException {
        for (int i = 0; i < targetFiles.size(); i++) {
            Files.copy(targetFiles.get(i), secondFiles.get(i));
        }
    }

}
