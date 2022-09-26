package portfolio;
import java.util.Random;

public class zundoko {
    public static void main(String[] args) {
        Random rand = new Random();
        int zunCounter = 0;
        int dokoCounter = 0;
        int total = 0;
        String[] zundoko = {"ズンッ", "ドコッ"};


        do{
            int num = rand.nextInt(2);
            if(zunCounter > 4 || dokoCounter > 2){
                dokoCounter = 0;
                zunCounter = 0;
                total=0;
            }

            if(num == 0) {
                System.out.println(zundoko[0]);
                zunCounter++;
                total++;
            } else {
                System.out.println(zundoko[1]);
                dokoCounter++;
                total*=2;
            }

            if(zunCounter == 4 && dokoCounter == 1 && total == 8){
                System.out.println("きよしっ！！");
                break;
            }
        } while (true);

    }
    
}


// zun zun zun zun doko 8  
// zun zun zun doko zun 7  
// zun zun doko zun zun 6  
// zun doko zun zun zun 4
// doko zun zun zun zun 5