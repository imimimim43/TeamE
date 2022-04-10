package huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

public class huffman {
    
    
    private static Map<Character, String> prefixCodeTable = new HashMap<>();    // Binary PrefixCode by Character (HashMap)

    public static void main(String args[]){
        // Origin text data
    	File file=new File("C:/Users/dlqnd/AliceinWonderland.txt");
    	Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String data = scan.nextLine();
        
        //System.out.println("Original data : " + data);

        // Huffman Encoding
        String encodeData = encode(data);
        //System.out.println("Encoded data : " + encodeData);

        // Huffman Decoding
        String decodeData = decode(encodeData);
        //System.out.println("Decoded data : " + decodeData);
        //System.out.println();

        // Print Report
        int originDataByteSize = data.getBytes(StandardCharsets.UTF_8).length;
        System.out.println("Original data size : " + originDataByteSize * 8 + "Bit (" + originDataByteSize + "Byte)");
        int encodeDataByteSize = encodeData.length() % 8 == 0 ? encodeData.length() / 8 : encodeData.length() / 8 + 1;
        System.out.println("Encoded data size : " + encodeData.length() + "bit (" + encodeDataByteSize + "Byte)");
    }
    

    // Encoding method
    public static String encode(String data){
        // Get Frequency by Character
        Map<Character, Integer> charFreq = new HashMap<>();
        for(char c : data.toCharArray()){
            if(!charFreq.containsKey(c)){
                charFreq.put(c, 1);
            }else{
                int no = charFreq.get(c);
                charFreq.put(c, ++no);
            }
        }
        System.out.println("Frequency by Character : " + charFreq);

        // Build Huffman Tree
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        Set<Character> keySet = charFreq.keySet();
        for(char c : keySet){
            Node node = new Node(c, charFreq.get(c));
            priorityQueue.offer(node);      // Add priorityQueue by char's freq
        }
        Node rootNode = buildTree(priorityQueue);       // Recursive Call

        // Set Prefix Code by Character
        System.out.println("Prefix Code Table");
        setPrefixCode(rootNode, "");            // Recursive Call

        // Convert Origin data to Prefix code
        StringBuilder sb = new StringBuilder();
        for(char c : data.toCharArray()){
            sb.append(prefixCodeTable.get(c));
        }

        return sb.toString();
    }

    // Decoding method
    public static String decode(String encodeData){
        StringBuilder sb = new StringBuilder();
        String tmp = "";
        for(char c : encodeData.toCharArray()){
            tmp += c;

            if(prefixCodeTable.containsValue(tmp)){
                Stream<Character> keyStream = getKeysByValue(prefixCodeTable, tmp);
                char key = keyStream.findFirst().get();
                sb.append(key);
                tmp = "";
            }
        }
        return sb.toString();
    }

    // Build Huffman Tree Recursive method
    public static Node buildTree(PriorityQueue<Node> priorityQueue){
        if(priorityQueue.size() == 1){
            return priorityQueue.poll();
        }else{
            Node leftNode = priorityQueue.poll();
            Node rightNode = priorityQueue.poll();

            Node sumNode = new Node();
            sumNode.cData = '`';
            sumNode.frequency = leftNode.frequency + rightNode.frequency;
            sumNode.left = leftNode;
            sumNode.right = rightNode;

            priorityQueue.offer(sumNode);
            return buildTree(priorityQueue);
        }
    }

    // Set Prefix Code Recursive method
    public static void setPrefixCode(Node node, String code){
        if(node == null){
            return;
        }

        if(node.cData != '`' && node.left == null && node.right == null){
            prefixCodeTable.put(node.cData, code);
            System.out.println("- " + node.cData + "(" + node.frequency + ") = " + code);
        }else{
            setPrefixCode(node.left, code + '0');
            setPrefixCode(node.right, code + '1');
        }
    }

    public static <K, V> Stream<K> getKeysByValue(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }
}

// Node of Huffman Tree
class Node implements Comparable<Node> {
    char cData;
    int frequency;
    Node left, right;

    Node(){}
    Node(char cData, int frequency){
        this.cData = cData;
        this.frequency = frequency;
    }

    // For Sorting priorityQueue
    @Override
    public int compareTo(Node node) {
        return frequency - node.frequency;
    }
}
