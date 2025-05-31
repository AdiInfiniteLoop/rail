package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;

public class Train {
    @JsonProperty("train_id")
    private String trainId;
    @JsonProperty("train_no")
    private String trainNo;
    private List<List<Integer>> seats;
    @JsonProperty("station_times")
    private HashMap<String, Time> stationTimes;
    private List<String> stations;

    public Train(String trainId, String trainNo, List<List<Integer>> seats, HashMap<String, Time> stationTimes, List<String> stations) {
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seats = seats;
        this.stationTimes = stationTimes;
        this.stations = stations;
    }

    public Train() {}

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Integer>> seats) {
        this.seats = seats;
    }

    public HashMap<String, Time> getStationTimes() {
        return stationTimes;
    }

    public void setStationTimes(HashMap<String, Time> stationTimes) {
        this.stationTimes = stationTimes;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public String getTrainInfo() {
        return String.format("Train ID: %s, TrainNo: %s", trainId, trainNo);
    }
}
