package portfolio;
import java.util.Scanner;

public class shindan {
    public static void main(String[] args) {
        Scanner stdIn = new Scanner(System.in);

        String[] question = {
            "猫派...1/犬派...2 : ",
            "朝型...1/夜型...2 : ",
            "パン派...1/米派...2 : "
        };
        int[] hand = new int[question.length];
        int retry;



        do{
            for(int i = 0; i < question.length; i++){
                do{
                    System.out.print(question[i]);
                    hand[i] = stdIn.nextInt();
                    
                    if(hand[i] > 2 || hand[i] < 1){
                        System.out.println("正しく入力してください。");
                    }
                }while(hand[i] > 2 || hand[i] < 1);
            }

                int sum = 0;
                for (int num : hand){
                    sum += num;
                }
                switch(sum){
                    case 3 : System.out.println("あなたはトラです"); break;
                    case 4 : System.out.println("あなたはパンダです"); break;
                    case 5 : System.out.println("あなたはキリンさんです"); break;
                    case 6 : System.out.println("あなたはゾウです"); break;
                }
           
            System.out.println("もう一度？ 1...Yes/0...No : ");
            retry = stdIn.nextInt();
        }while(retry == 1);

        System.out.println("お疲れ様でした。");
        
    }
    
}
