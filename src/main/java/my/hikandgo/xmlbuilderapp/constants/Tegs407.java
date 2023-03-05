package my.hikandgo.xmlbuilderapp.constants;

public enum Tegs407 {
    DATE_MSG ("<ДатаСооб>", "</ДатаСооб>"),
    DATE_REQUEST ("<ДатЗапр>", "</ДатЗапр>"),
    DATE_GET_REQ ("<ДатПолучЗапр>", "</ДатПолучЗапр>"),
    DATE_ORDER ("<ДатПоручФил>", "</ДатПоручФил>"),
    DATE_PROLONG ("<ДатаПролонг>", "</ДатаПролонг>"),
    INV_NAME ("<ИмФайлВлож>", "</ИмФайлВлож>");


    private final String tegOpen;
    private final String tegClose;

    Tegs407(String tegOpen, String tegClose) {
        this.tegOpen = tegOpen;
        this.tegClose = tegClose;
    }

    public String getTegOpen() {
        return tegOpen;
    }

    public String getTegClose() {
        return tegClose;
    }
}


