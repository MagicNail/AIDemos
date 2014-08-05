package decisionTree;

import java.util.*;
import java.io.*;
/**
 * Used to split the hepatitis data because the shell script did not work for me
 * 
 * @author Jeff
 *
 */
public class DataSplitter {
	public static void main(String args[]){
		try {
			Scanner sc = new Scanner(new File(args[0]));
			String header = sc.nextLine()+"\n"+sc.nextLine()+"\n";
			List<String> data = new ArrayList<String>();
			while(sc.hasNext()){
				data.add(sc.nextLine());
			}
			Collections.shuffle(data);
			int breakPoint = Integer.parseInt(args[1]);
			BufferedWriter out = new BufferedWriter(new FileWriter("training-"+args[2]));
			out.write(header);
			for(int i=0;i<breakPoint;i++){
				out.write(data.get(i)+"\n");
			}
			out.close();
			out = new BufferedWriter(new FileWriter("test-"+args[2]));
			out.write(header);
			for(int i=breakPoint;i<data.size();i++){
				out.write(data.get(i)+"\n");
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
