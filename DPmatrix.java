package matrix;

public class DPmatrix {
	
	//M1*M2*...*Mn계산을 위한 최소 곱셈횟수
	//동적계획
	//행렬의 수 n
	//Mi, i=1,..n, r[i-1]*r[i]행렬
	
  static int MatrixMult_DP(int r[],int n) {
		
    //배열 m의 첫번쨰 행,열 사용 안함
	int m[][]=new int [n][n];
	int i,j,k,len,q;
    //행렬이 하나만 있을 때 곱할 필요 없음		 
	for(i=1;i<n;i++) {
		m[i][i]=0;
	}
    //len : 행렬 곱셈들의 수
	for(len=1;len<n;len++) {
		for(i=1;i<n-len;i++) {
			j=i+len;
			m[i][j]=Integer.MAX_VALUE/2;//최솟값비교위한 MAX값
				
			for(k=i;k<j;k++) {
				//q=두개의 행렬 곱으로 분할한 후 곱하는경우 최소 곱셈횟수
				q=m[i][k]+ m[k+1][j]+r[i-1]*r[k]*r[j];
				if(q<m[i][j]) {
					m[i][j]=q;
				}
			}
		}
	}
	return m[1][n-1];
}
  
  public static void main(String[] args) {
	int N;
	int[] arr;		
	//입력받을 d의 개수 : N
	Scanner scanner=new Scanner(System.in);
	N=scanner.nextInt();
	arr=new int[N];
	//숫자 입력 받기
	//d0 d1 ....dN
	//A1=d0*d1 A2=d1*d2 A3=d2*d3 A4=d3*d4 ...AN=d(N-1)*d(N)
	for (int i=0;i<N;i++) {
		arr[i]=scanner.nextInt();
	}
	scanner.close();
		
   	int size=arr.length;
	System.out.println("동적 계획을 이용한 최소곱셈 : "+ MatrixMult_DP(arr,size));

  }

}
