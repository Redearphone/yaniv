package portfolio.yaniv;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;




public class yanivgame {

     // デッキのリストを生成
     private static List <Integer> deck = new ArrayList<>(52);


     // 捨て場所のリストを生成
     private static List <Integer> trashDeck = new ArrayList<>();


    // 捨て場所カウント
    private static int trashCount = 0;

    // 終了用カウント
    private static int winner = 0;

    

    public static void main(String[] args) {
        Scanner stdIn = new Scanner(System.in);
        System.out.println("テスト");

        shuffleDeck(deck);

        trashDeck.add(deck.get(15));


        // プレイヤーとcpuの手札リストを生成
        List <Integer> player = new ArrayList<>();
        List <Integer> cpu1 = new ArrayList<>();
        List <Integer> cpu2 = new ArrayList<>();

        // デッキのカウント
        int deckCount = 1;

        // プレイヤーとcpuが５枚ずつカードを引く
        for(int i = 0; i < 15; i++){
            switch(i%3){
                case 0: player.add(deck.get(i));  break;
                case 1: cpu1.add(deck.get(i));  break;
                case 2: cpu2.add(deck.get(i));  break;
            }
            deckCount++;
        }
        do{
            // -------------------playerの番----------------------- //
            System.out.println("playerの番です。");
            int playersum = totalHand(player);
            if(playersum <= 5){
                System.out.println("やにふ！！！！");
                winner = 1;
                break;
            } 
            System.out.print("現在のあなたの手札 : ");
            handOpen(player);
            int sum = totalHand(player);
            System.out.println("合計は" + sum + "です。");

            int selecthand;
            do{ 
                System.out.println("どちらから引きますか？ 山札...0 / 捨て場所([" + trashDeck.get(trashCount) + "])...1"); 
                selecthand = stdIn.nextInt(); 
                if(!(selecthand == 0 || selecthand == 1)){
                    System.out.println("「 0 」か「 1 」で入力してください。");
                }
            } while( !(selecthand == 0 || selecthand == 1));
            if(selecthand == 0) {
                deckAdd(player, deck, deckCount);
                deckCount++;
            } else if(selecthand == 1) {
                trashAdd(player, trashDeck, trashCount);
                trashCount--;
                // System.out.println("＊確認用trashcount" + trashCount);

            }
            handOpen(player);

            System.out.println("次に手札を１枚捨てます。");
            System.out.println("どのカードを捨てますか？");

            int selectNum;
            boolean checkNum;
            do{
                selectNum = stdIn.nextInt();
                checkNum = false;
                
                for(int i=0; i < player.size(); i++){
                    int playerHand = player.get(i);
                    if(selectNum == playerHand) {
                    checkNum = true;
                    } 
                }
                if(checkNum == false){
                    System.out.println("手札にある数を入力してください。"); 
                }
            }while(checkNum == false);
                
                trashCard(player, selectNum, trashDeck);
            
                trashCount++;
                // System.out.println("＊確認用trashcount" + trashCount);
            


            for(int i = 0; i < player.size(); i++) {
                if (selectNum == player.get(i)) {
                    System.out.println("同じカードがありますが、もう１枚捨てますか？ はい...0 / いいえ...1");
                    do{ 
                        selecthand = stdIn.nextInt();
                        if(!(selecthand == 0 || selecthand == 1)){
                            System.out.println("「 0 」か「 1 」で入力してください。");
                        }        
                    } while( !(selecthand == 0 || selecthand == 1));
                    if (selecthand == 0){
                        trashCard(player, selectNum, trashDeck);
                        trashCount++;
                        // System.out.println("＊確認用trashcount" + trashCount);

                        System.out.println("[" + player.get(i) + "]を捨てました。");
                        break;
                    }
                }
            }

            handOpen(player);
            System.out.println("-----------------------playerのターン終了-------------------------");
            System.out.println();



            // -------------------cpu1の手番----------------------- //
            int cpu1sum = totalHand(cpu1);
            if(cpu1sum <= 5){
                System.out.println("やにふ！！！！");
                handOpen(cpu1);
                System.out.println("合計 (" + cpu1sum + ")");
                winner = 2;
                break;
            } 


            System.out.println("ケビンの番です。");
            System.out.println();
            // 山札か捨て札かから引く
            int checkCpu1 = trashCount;
            // 捨て場所に同じ数があるとき
            for(int i = 0; i < cpu1.size(); i++){
                if(trashDeck.get(trashCount) == cpu1.get(i)) {
                    System.out.println("ケビンは捨て場所から引きました。");
                    trashAdd(cpu1,trashDeck,trashCount);
                    trashCount--;
                    // System.out.println("＊確認用trashcount" + trashCount);

                    break;
                } 
            }

            // 捨て場所に同じ数がないとき
            if(trashCount == checkCpu1){
                if(minNum(cpu1) > trashDeck.get(trashCount) ){
                    System.out.println("ケビンは捨て場所から引きました。");
                    trashAdd(cpu1,trashDeck,trashCount);
                    trashCount--;
                    // System.out.println("＊確認用trashcount" + trashCount);

                } else {
                    // 捨て場所の数が手札の一番小さい数より大きいとき山札から引く
                    System.out.println("ケビンは山札から引きました。");
                    deckAdd(cpu1, deck, deckCount);
                    deckCount++;
                }
            }

            // 手札から一枚捨てる
            int maxCpu1 = maxNum(cpu1); 

            trashCard(cpu1, maxCpu1, trashDeck);
            trashCount++;

            for(int i = 0; i < cpu1.size(); i++) {
                if(maxCpu1 == cpu1.get(i)) {
                    trashCard(cpu1, maxCpu1, trashDeck);
                    trashCount++;
                    // System.out.println("＊確認用trashcount" + trashCount);

                    break;
                }
            }

            System.out.println("ケビンのカード枚数は「" + cpu1.size() + "」枚です。");
            System.out.println();
            for (int i=0; i<cpu1.size(); i++){
                System.out.print("["+cpu1.get(i)+ "] ");
            }
            System.out.println("-----------------------ケビンのターン終了-------------------------");
            System.out.println();



            // -------------------cpu2の手番----------------------- //
            int cpu2sum = totalHand(cpu2);
            if(cpu2sum <= 5){
                System.out.println("やにふ！！！！");
                handOpen(cpu2);
                System.out.println("合計 (" + cpu2sum + ")");
                winner = 3;
                break;
            } 
    
    
            System.out.println("ワトソン君の番です。");
            System.out.println();
            // 山札か捨て札かから引く
            int checkCpu2 = trashCount;
            // 捨て場所に同じ数があるとき
            for(int i = 0; i < cpu2.size(); i++){
                if(trashDeck.get(trashCount) == cpu2.get(i)) {
                    System.out.println("ワトソン君は捨て場所から引きました。");
                    trashAdd(cpu2,trashDeck,trashCount);
                    trashCount--;
                    System.out.println("＊確認用trashcount" + trashCount);

                    break;
                } 
            }
    
            // 捨て場所に同じ数がないとき
            if(trashCount == checkCpu2){
                if(minNum(cpu2) > trashDeck.get(trashCount) ){
                    System.out.println("ワトソン君は捨て場所から引きました。");
                    trashAdd(cpu2,trashDeck,trashCount);
                    trashCount--;
                    System.out.println("＊確認用trashcount" + trashCount);
                } else {
                    // 捨て場所の数が手札の一番小さい数より大きいとき山札から引く
                    System.out.println("ワトソン君は山札から引きました。");
                    deckAdd(cpu2, deck, deckCount);
                    deckCount++;
                }
            }
    
            // 手札から一枚捨てる
            int maxCpu2 = maxNum(cpu2); 
    
            trashCard(cpu2, maxCpu2, trashDeck);
            trashCount++;
    
            for(int i = 0; i < cpu2.size(); i++) {
                if(maxCpu2 == cpu2.get(i)) {
                    trashCard(cpu2, maxCpu2, trashDeck);
                    trashCount++;
                    System.out.println("＊確認用trashcount" + trashCount);
                    break;
                }
            }
    
            System.out.println("ワトソン君のカード枚数は「" + cpu2.size() + "」枚です。");
            System.out.println();
        for (int i=0; i<cpu2.size(); i++){
            System.out.print("["+cpu2.get(i)+ "] ");
        }
            System.out.println("-----------------------ワトソン君のターン終了-------------------------");
            System.out.println();
        } while(true);

        switch(winner) {
            case 0 : System.out.println("正常に終わってません。");  break;
            case 1 : System.out.println("playerの勝利です。");  break;
            case 2 : System.out.println("ケビン君の勝利です。");   break;
            case 3 : System.out.println("ワトソン君の勝利です。");  break;
        }
 

        // cpu確認用
        System.out.println();
        for (int i=0; i<cpu1.size(); i++){
            System.out.print(cpu1.get(i)+ ",");
        }
        System.out.println();
        for (int i=0; i<cpu2.size(); i++){
            System.out.print(cpu2.get(i)+ ",");
        }

    }

        // System.out.println();
        // for (int i=0; i<cpu1.size(); i++){
        //     System.out.print(cpu1.get(i)+ ",");
        // }
        // System.out.println();
        // for (int i=0; i<cpu2.size(); i++){
        //     System.out.print(cpu2.get(i)+ ",");
        // }




//デッキをシャッフルするメソッド 

    private static void shuffleDeck(List<Integer> deck) {

    //リストに１～１３の数字を4つ代入
    for(int i = 0; i <= 3; i++){
        for(int j = 1; j <= 13; j++){
            deck.add(j);
        }
    }

    deck.add(0);
    deck.add(0);

    // 山札をシャッフル
    Collections.shuffle(deck);

    // // リストの中身を確認（デバック用）
    // for (int i=0; i<deck.size(); i++)
    // {
    //     System.out.println(deck.get(i));
    // }
    
}



    // 手札を表示するメソッド
    public static void handOpen(List<Integer> player){
        for (int i=0; i<player.size(); i++){
            System.out.print("[" + player.get(i) + "]　");
        }
        System.out.println();
    }




    //　手札の数を合計するメソッド
    public static int totalHand(List<Integer> deck) {
        int total = 0;
        for(int i = 0; i < deck.size(); i++){
            total += deck.get(i);
        }
        return total;
    }


    // カードを捨てるメソッド
    public static void trashCard (List<Integer> player , int selectNum , List<Integer> trashDeck) {
        boolean checkNum = false;
        do{
            for(int i=0; i < player.size(); i++){
                int playerHand = player.get(i);
                trashDeck.add(playerHand);
                if(selectNum == playerHand) {
                    checkNum = true;
                    player.remove(i);
                    break;
                } 
            }
            
        }while(checkNum == false);
    }

    // 入力したカードと数があっているか確かめるメソッド
    public static void CheckNum(int a, List<Integer> player){
        boolean b = false;
        do{
            for(int i=0; i < player.size(); i++){
                int playerHand = player.get(i);
                if(a == playerHand) {
                b = true;
                } 
            }
            System.out.println("手札にある数を入力してください。"); 
        }while(b == false);
    }


    // cpu手札の一番少ない数を求めるメソッド
    public static int minNum (List<Integer> cpu) {
        int min = 0;
        for(int i = 0; i < cpu.size(); i++){
            if(min > cpu.get(i)){
                min = cpu.get(i);
            }
        }
        return min;
    }


    // cpu手札の一番大きい数を求めるメソッド
    public static int maxNum (List<Integer> cpu) {
        int max = 0;
        for(int i = 0; i < cpu.size(); i++){
            if(max < cpu.get(i)){
                max = cpu.get(i);
            }
        }
        return max;
    }


    // 捨て場所から引くメソッド
    public static void trashAdd(List<Integer> hand , List<Integer> trashDeck, int trashCount) {
        hand.add(trashDeck.get(trashCount));
        trashDeck.remove(trashCount);
    }

    // 山札から引くメソッド
    public static void deckAdd(List<Integer> hand, List<Integer> deck, int deckCount){
        hand.add(deck.get(deckCount));
    }

}


