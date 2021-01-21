package edu.ib.zpo13;

public class COVIDData {

    private String country;
    private int cases;
    private int active;
    private int casesPerOneMillion;
    private int testsPerOneMillion;

    public COVIDData(String country, int cases, int active, int casesPerOneMillion, int testsPerOneMillion) {
        this.country = country;
        this.cases = cases;
        this.active = active;
        this.casesPerOneMillion = casesPerOneMillion;
        this.testsPerOneMillion = testsPerOneMillion;
    }


    public String getCountry() {
        return country;
    }

    public int getCases() {
        return cases;
    }

    public int getActive() {
        return active;
    }

    public int getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public int getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    @Override
    public String toString() {
        return "country='" + country + '\'' +
                ", cases=" + cases +
                ", active=" + active +
                ", casesPerOneMillion=" + casesPerOneMillion +
                ", testPerOneMillion=" + testsPerOneMillion;
    }
}
