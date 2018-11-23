package CacheManager;

import Solver.Solution;

import java.io.*;
import java.util.HashMap;

public class MyCacheManager implements CacheManager {
    //Create cache file
	public File PSCache = new File("PSCache.txt");
	private HashMap<String, Solution> solutions = new HashMap<String, Solution>();

	
	//Generate solutions form file (cache) to HashMap
    public MyCacheManager() {
        try {
            File f = new File("PSCache");
            if (f.isFile() && f.canRead()) {
                FileInputStream file = new FileInputStream(PSCache);
                ObjectInputStream inputStream = new ObjectInputStream(file);
                solutions = (HashMap<String, Solution>) inputStream.readObject();
                file.close();
                inputStream.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    @Override
    public Solution load(String level) {
        if (solutions.containsKey(level)) {
            return solutions.get(level);
        }
        return null;
    }
    
    
    @Override
    public void save(String problem, Solution solution) {
        if (!solutions.containsKey(problem)) {
        	solutions.put(problem, solution);

            OutputFile();
        }
    }

    //Dump HashMap to file (cache)
    private void OutputFile() {

        try {
            FileOutputStream file = new FileOutputStream(PSCache);
            ObjectOutputStream oos = new ObjectOutputStream(file);

            oos.writeObject(solutions);

            oos.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}