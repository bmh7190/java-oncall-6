package oncall.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Schedule {

    Map<LocalDate, Work> work = new HashMap<>();

    public void addDate(LocalDate date){
        work.put(date, new Work());
    }

    public void addWorker(LocalDate date, String name){
        Work worker = work.get(date);

        if(!worker.isAssigned()){
            worker.assign(name);
        }
    }

    public String getWorker(LocalDate date){
        return work.get(date).getWorker();
    }

    public Map<LocalDate, Work> getWork(){
        return work;
    }

    public boolean check(LocalDate now, LocalDate next){

        String nowWorker = work.get(now).getWorker();
        String nextWorker = work.get(next).getWorker();

        return !nowWorker.equals(nextWorker);
    }

    public void swap(LocalDate a, LocalDate b) {
        String workerA = getWorker(a);
        String workerB = getWorker(b);

        addWorker(a, workerB);
        addWorker(b, workerA);
    }

}
