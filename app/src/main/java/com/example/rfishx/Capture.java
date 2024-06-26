package com.example.rfishx;

public class Capture {
    private String userId;
    private String shipName;
    private String fishName;
    private String captureDate;
    private double fishWeight;
    private String captureLocation;
    private String fishingBase;
    private double jarak;
    private double fishingBaseLatitude;
    private double fishingBaseLongitude;
    private double fishingGroundLatitude;
    private double fishingGroundLongitude;

    public Capture() {
        // Diperlukan untuk Firestore
    }

    public Capture(String userId, String shipName, String fishName, String captureDate, double fishWeight, String captureLocation,
                   String fishingBase, double jarak, double fishingBaseLatitude, double fishingBaseLongitude,
                   double fishingGroundLatitude, double fishingGroundLongitude) {
        this.userId = userId;
        this.shipName = shipName;
        this.fishName = fishName;
        this.captureDate = captureDate;
        this.fishWeight = fishWeight;
        this.captureLocation = captureLocation;
        this.fishingBase = fishingBase;
        this.jarak = jarak;
        this.fishingBaseLatitude = fishingBaseLatitude;
        this.fishingBaseLongitude = fishingBaseLongitude;
        this.fishingGroundLatitude = fishingGroundLatitude;
        this.fishingGroundLongitude = fishingGroundLongitude;
    }

    // Getter dan Setter untuk semua atribut
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getShipName() { return shipName; }
    public void setShipName(String shipName) { this.shipName = shipName; }

    public String getFishName() { return fishName; }
    public void setFishName(String fishName) { this.fishName = fishName; }

    public String getCaptureDate() { return captureDate; }
    public void setCaptureDate(String captureDate) { this.captureDate = captureDate; }

    public double getFishWeight() { return fishWeight; }
    public void setFishWeight(double fishWeight) { this.fishWeight = fishWeight; }

    public String getCaptureLocation() { return captureLocation; }
    public void setCaptureLocation(String captureLocation) { this.captureLocation = captureLocation; }

    public String getFishingBase() { return fishingBase; }
    public void setFishingBase(String fishingBase) { this.fishingBase = fishingBase; }

    public double getJarak() { return jarak; }
    public void setJarak(double jarak) { this.jarak = jarak; }

    public double getFishingBaseLatitude() { return fishingBaseLatitude; }
    public void setFishingBaseLatitude(double fishingBaseLatitude) { this.fishingBaseLatitude = fishingBaseLatitude; }

    public double getFishingBaseLongitude() { return fishingBaseLongitude; }
    public void setFishingBaseLongitude(double fishingBaseLongitude) { this.fishingBaseLongitude = fishingBaseLongitude; }

    public double getFishingGroundLatitude() { return fishingGroundLatitude; }
    public void setFishingGroundLatitude(double fishingGroundLatitude) { this.fishingGroundLatitude = fishingGroundLatitude; }

    public double getFishingGroundLongitude() { return fishingGroundLongitude; }
    public void setFishingGroundLongitude(double fishingGroundLongitude) { this.fishingGroundLongitude = fishingGroundLongitude; }
}
