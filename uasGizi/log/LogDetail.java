package uasGizi.log;

public class LogDetail {
    private String tanggal;
    private String namaMakanan;
    private String beratGram;
    private String totalKalori;
    private String totalProtein;
    private String totalLemak;
    private String totalKarbo;

    public LogDetail(String tanggal, String namaMakanan, String beratGram,
                     String totalKalori, String totalProtein,
                     String totalLemak, String totalKarbo) {
        this.tanggal = tanggal;
        this.namaMakanan = namaMakanan;
        this.beratGram = beratGram;
        this.totalKalori = totalKalori;
        this.totalProtein = totalProtein;
        this.totalLemak = totalLemak;
        this.totalKarbo = totalKarbo;
    }

    public String getTanggal() { return tanggal; }
    public String getNamaMakanan() { return namaMakanan; }
    public String getBeratGram() { return beratGram; }
    public String getTotalKalori() { return totalKalori; }
    public String getTotalProtein() { return totalProtein; }
    public String getTotalLemak() { return totalLemak; }
    public String getTotalKarbo() { return totalKarbo; }
}
