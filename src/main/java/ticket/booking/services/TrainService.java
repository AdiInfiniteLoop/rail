package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class TrainService {
    private Train train;
    private static final String TRAIN_PATH = "src/main/java/ticket/booking/localDB/trains.json";
    private List<Train> trainLists;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public TrainService() throws IOException {
        loadTrains();
    }

    private void loadTrains() throws IOException {
        File trains = new File(TRAIN_PATH);
        trainLists = OBJECT_MAPPER.readValue(trains, new TypeReference<>(){});
    }

    public List<Train> getTrains(String source, String destination) {
        List<Train> res = new ArrayList<>();
        for (Train train : trainLists) {
            List<String> stations = train.getStations();
            int idx1 = stations.indexOf(source);
            int idx2 = stations.indexOf(destination);
            if(idx1 != -1 && idx2 != -1 && idx1 < idx2){
                res.add(train);
            }
        }
        return res;
    }

    public void addTrain(Train newTrain) {
        Optional<Train> presentTrain = trainLists.stream().filter(train -> train.getTrainId().equals(newTrain.getTrainId())).findFirst();

        if(presentTrain.isPresent()){
            // If a train with the same trainId exists, update it instead of adding a new one
        } else {
            trainLists.add(newTrain);
            saveTrainListToFile();
        }
    }
    private void saveTrainListToFile() {
        try {
            OBJECT_MAPPER.writeValue(new File(TRAIN_PATH), trainLists);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateTrain(Train trainToUpdate) {
        OptionalInt index = IntStream.range(0, trainLists.size()).filter(i -> trainLists.get(i).getTrainId().equalsIgnoreCase(trainToUpdate.getTrainId())).findFirst();
        if(index.isPresent()) {
            trainLists.set(index.getAsInt(), trainToUpdate);
            saveTrainListToFile();
        }
        else {
            trainLists.add(trainToUpdate);
        }
    }
}
