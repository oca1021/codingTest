//List를 사용하기 위해 java.util을 import 한다.
import java.util.*; 

/**
 *   writer : Jea
 */


class FailureProbability {
    //실패율을 저장하기 위해서 스테이지와 함께 저장을 해야하기 때문에, 실패율을 정렬할때 실패율이 같은경우에는 스테이지가 작은것 부터 출력을 해야하고 정렬을 한 이후에 출력해야하는 답이 실패율이 아니라 스테이지의 번호가 되기 때문에 Fail이란 클래스를 선언한다.
    class Fail {        
        int stage;       
        double rate;
        // Fail이란 클래스를 두가지 값으로 객체를 생성할수 있도록 생성자를 선언
        Fail(int stage, double rate) {
            this.stage = stage;
            this.rate = rate;
        }
    }
    
    //Comparator 객체 생성
    Comparator<Fail> comp = new Comparator<Fail>() {
        // compare 메소드를 구현해야함
        public int compare(Fail a, Fail b) { 
            if (a.rate < b.rate) 
                return 1;
            else if (a.rate > b.rate)
                return -1;
            else {
                if (a.stage > b.stage) //stage 번호가 작은게 앞에올 수 있도록
                    return 1;
                else if (a.stage < b.stage)
                    return -1;
                else
                     return 0;                
            }
        }
    };
    
    // int N : 스테이지의 개수
    // int[] stages : 각각의 유저가 몇번째 스테이지에 도달했는지 나타내는 stages 배열이다. 
    // stages 길이는 유저의 명수가 되고 각 유저가 지금 도달한 어느 stage까지 도달했는가를 나타냄
    public int[] solution(int N, int[] stages) { 
        // answerArray 크기도 stage 개수와 같다.
        int[] answer = new int[N]; 
        // Fail타입으로 fails라는 List를 선언
        List<Fail>fails = new ArrayList<Fail>();
        // 실패율을 구하기 위해 전체 사용자 명수가 필요, 진짜 사용자 명수는 입력으로 받은 스테이지 배열의 크기와 같다.
        int total = stages.length;
        // 그값은 users라는 Array에 저장 할것, users에 인덱스가 스테이지 번호이고, 그 값은 그 스테이지에 도달한 사용자가 된다.  
        int[] users = new int[N+1]; 
        // 스테이지를 순회를 하면서 각 스테이지에 도달한 사용자를 카운팅 함
        for (int s : stages)
            users[s-1]++; // 순회를 하면서 스테이지 값으로 인덱스로 해서 하나씩 값을 증가시켜주면 되는데 0부터 쓰기위해 1을 빼서 쓴다 1번스테이지에 사용자 수는 0번 인덱스에 저장이 된다. 
        
        // users array를 순회하면서 각각의 스테이지에 실패율을 구한다.
        for (int i=0; i<N; ++i) {
            // 아이가 0이면 첫번째 스테이지에 도달한 사용자수가 된다. 
            if (users[i] == 0)
                // 지문에서 실패율은 무조건 0 으로 하도록 되어있다. 
                fails.add(new Fail(i+1, 0)); // 위에서 1을 빼서 사용했기 때문에 다시 1을 더한값을 쓰면서 원래스테이지 번호를 돌려준다. 실패율은 0으로 쓰고 페일즈 리스트에 추가한다.
            else {
                fails.add(new Fail(i+1, (double)users[i]/total)); //현재스테이지에 머물고 있는 수/전체 사용자 수 로 나눈다. 전체결과가 실수로 되도록 하기위해 (double)을 쓴다.
                total -= users[i]; //  전체 사용자 수에서 현재 스테이지에 머물고 있는 사용자 수를 빼준다.
            }
        }
        
        // 실패율에 따라 정렬(실패율이 큰 순서대로 앞으로 오도록)
        Collections.sort(fails, comp);
        for (int i=0; i<N; ++i)
         // 앞에서부터 차례대로 fails의 값을 가져와서 stage만 answer에 넣어주면 된다.   
            answer[i] = fails.get(i).stage; 
            
        // answer array 크기도 결국 스테이지의 개수와 같다.  
        return answer;
    }
}

