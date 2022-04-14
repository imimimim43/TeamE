package test;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
//���� �ʿ������ ����� �� �� �߰���

//���
class Node
{
	public char ch; //Ʈ������ ���� �ϳ� �� ��
	public int freq; //�� ������ �󵵼�
	public Node left, right; //�� ����� �����ʰ� ���� ���(Ʈ��)
}

//�󵵼� ���� �Լ�
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
	public static PriorityQueue<Node> queue;//�켱���� ť
	public static HashMap <Character, String> charToCode = new HashMap <Character, String>(); //�ؽ� ����ϱ� ���� �����(���ڿ� ���� �ڵ� ��)
	
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
	
	//������ ��忡�� �󵵼��� �̿��ؼ� Ʈ���� ����� ��
	public static Node huffman_freq_a(int n)
	{
		for(int i = 0; i<n-1; i++) //2���� ��带 ���� �󵵼��� ��ġ�� Ʈ���� �ø�
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
		//���̶� ��ģ �κ�(�ٸ� ���̺귯���� ���� ����)
		String text = Files.readString(Paths.get("C:\\Users\\user\\asdf.txt"));//������ �о� String���� �ٲٱ�
		
		HashMap <Character, Integer> dictionary = new HashMap <Character, Integer>(); //������ ���ڿ� ���� �󵵼��� Ȯ�� �� ����
		
		for(int i=0; i<text.length(); i++)
		{
			char temp = text.charAt(i);
			
			if(dictionary.containsKey(temp))//���� �̹� Ȯ�� �� ���ڶ��
			{
				dictionary.put(temp, dictionary.get(temp)+1);//temp���ٰ� +1�ϱ�
			}
			else//�װ� �ƴ϶��
			{
				dictionary.put(temp, 1);//temp��ü�� 1 �ֱ�
			}
		}
		
		queue = new PriorityQueue<Node>(100, new Freq_ch_Node());//��� ��带 �켱 ���� ť�� �߰�
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
		
		//����� ���
		int originalDataByteSize=text.length()*8;
		int encodeDataByteSize=result.length();
		double compressibility=encodeDataByteSize/(double)originalDataByteSize*100;
		System.out.println("Original Data Byte Size :" +originalDataByteSize*8+"bit");
		System.out.println("Encode Data Byte Size : "+encodeDataByteSize+"bit");
		System.out.println("The result Data Byte Size is compressed by "+compressibility+"%");
	}
}




































