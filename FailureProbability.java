import java.util.*;

class FailureProbability {
     //실패율을 저장하기 위해서 스테이지와 함께 저장을 해야하기 때문에
    class Fail {        
        int stage;      
        double rate;
        Fail(int stage, double rate) {
            this.stage = stage;
            this.rate = rate;
        }
    }
    
   // 왜냐하면 실패율을 정렬할때 실패율이 같은경우에는 스테이지가 작은것부터 출력을 해야하고 정렬을 한 이후에 출력해야하는 답이 실패율이 아니라 스테이지의 번호가 되기 때문에 페일이란 클래스를 선언한다. 패일이란 클래스를 두가지 값으로 객체를 생성할수 있도록 생성자를 선언한다.
    
    Comparator<Fail> comp = new Comparator<Fail>() {
        public int compare(Fail a, Fail b) {
            if (a.rate < b.rate)
                return 1;
            else if (a.rate > b.rate)
                return -1;
            else {
                if (a.stage > b.stage)
                    return 1;
                else if (a.stage < b.stage)
                    return -1;
                else
                     return 0;
                
            }
        }
    };
    
    public int[] solution(int N, int[] stages) { 
        // int N : 스테이지의 개수
        // int[] stages : 각각의 유저가 몇번째 스테이지에 도달했는지 나타내는 stages 배열이다. 
        // stages 길이는 유저의 명수가 되고 각 유저가 지금 도달한 어느스테이지까지 도달했는가를 나타내는것
        int[] answer = new int[N];  
        List<Fail>fails = new ArrayList<Fail>();
        int total = stages.length;
        
        int[] users = new int[N+1]; 
        for (int s : stages)
            users[s-1]++;
        
        for (int i=0; i<N; ++i) {
            if (users[i] == 0)
                fails.add(new Fail(i+1, 0));
            else {
                fails.add(new Fail(i+1, (double)users[i]/total)); //현재스테이지에 머물고 있는 수 
                total -= users[i];
            }
        }
        
        // 배열을 정렬
        Collections.sort(fails, comp);
        for (int i=0; i<N; ++i)
            answer[i] = fails.get(i).stage;
            
        // answer array 크기도 결국 스테이지의 개수와 같다.  
        return answer;
    }
}

