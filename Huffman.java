package test;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
//전에 필요없던거 지우고 몇 개 추가함

//노드
class Node
{
	public char ch; //트리에서 글자 하나 들어갈 것
	public int freq; //그 글자의 빈도수
	public Node left, right; //현 노드의 오른쪽과 왼쪽 노드(트리)
}

//빈도수 측정 함수
class Freq_ch_Node implements Comparator<Node>
{
	public int compare(Node a, Node b)
	{
		int freq_a = a.freq;
		int freq_b = b.freq;
		return freq_a-freq_b;
	}
}

public class Huffman
{
	public static PriorityQueue<Node> queue;//우선순위 큐
	public static HashMap <Character, String> charToCode = new HashMap <Character, String>(); //해시 사용하기 위해 만들기(문자에 따른 코드 값)
	
	public static void traversal(Node root, String string)
	{
		if(root==null)
			return;
		
		traversal(root.left, string + "0");
		traversal(root.right, string + "1");
		
		if(root.ch != '\0')
		{
			System.out.println(root.ch + "=" + string);
			charToCode.put(root.ch, string);
		}
	}
	
	//각각의 노드에서 빈도수를 이용해서 트리를 만드는 것
	public static Node huffman_freq_a(int n)
	{
		for(int i = 0; i<n-1; i++) //2개의 노드를 꺼내 빈도수를 합치고 트리를 올림
		{
			Node z= new Node();
			z.right = queue.poll();
			z.left = queue.poll();
			z.freq = z.right.freq + z.left.freq;
			queue.add(z);
		}
		return queue.poll();
	}
	
	public static void main(String[] args) throws IOException
	{
		//전이랑 고친 부분(다른 라이브러리도 같이 썼음)
		String text = Files.readString(Paths.get("C:\\Users\\user\\asdf.txt"));//파일을 읽어 String으로 바꾸기
		
		HashMap <Character, Integer> dictionary = new HashMap <Character, Integer>(); //각각의 문자에 대한 빈도수를 확인 할 변수
		
		for(int i=0; i<text.length(); i++)
		{
			char temp = text.charAt(i);
			
			if(dictionary.containsKey(temp))//만약 이미 확인 된 문자라면
			{
				dictionary.put(temp, dictionary.get(temp)+1);//temp에다가 +1하기
			}
			else//그게 아니라면
			{
				dictionary.put(temp, 1);//temp자체에 1 넣기
			}
		}
		
		queue = new PriorityQueue<Node>(100, new Freq_ch_Node());//모든 노드를 우선 순위 큐에 추가
		int n = 0;
		
		for(Character c : dictionary.keySet())
		{
			Node temp = new Node();
			temp.ch = c;
			temp.freq = dictionary.get(c);
			queue.add(temp);
			n++;
		}
		
		Node root = huffman_freq_a(n);
		traversal(root, new String());
		
		String result = new String();
		
		for(int i = 0; i<text.length(); i++)
			result = result + charToCode.get(text.charAt(i)) + " ";
		
		System.out.println(result+"\n");
		
		//압축률 출력
		int originalDataByteSize=text.length()*8;
		int encodeDataByteSize=result.length();
		double compressibility=encodeDataByteSize/(double)originalDataByteSize*100;
		System.out.println("Original Data Byte Size :" +originalDataByteSize*8+"bit");
		System.out.println("Encode Data Byte Size : "+encodeDataByteSize+"bit");
		System.out.println("The result Data Byte Size is compressed by "+compressibility+"%");
	}
}




































