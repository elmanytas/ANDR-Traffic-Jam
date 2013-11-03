package is.ru.TrafficJam;

/**
 * Created with IntelliJ IDEA.
 * User: AuðunVetle
 * Date: 26.10.2013
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */

import java.io.InputStream;
import java.util.ArrayList;

import android.graphics.Point;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import android.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;


public class XMLParser
{
    private static ArrayList<ArrayList<Block>> blocks = null;

    private static InputStream xmlInputStream = null;

    private XMLParser()
    {

    }

    public static ArrayList<Block> getLevel(int lvlNr)
    {
        if(blocks == null)
        {
            readXML();
        }
        ArrayList<Block> returnedList = new ArrayList<Block>();
        for(Block b : blocks.get(lvlNr-1))
        {
            returnedList.add(new Block(b));
        }
        return returnedList;
    }

    public static int getNumberOfLevels()
    {
        if(blocks == null)
        {
            readXML();
        }
        return blocks.size();
    }

    public static void setIOStream(InputStream io)
    {
        xmlInputStream = io;
    }


    private static void readXML()
    {
        Log.d("XMLParser", "Starting to parse");
        blocks = new ArrayList<ArrayList<Block>>();

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlInputStream);


            doc.getDocumentElement().normalize(); //optional

            Log.d("XMLParser", "Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nodes = doc.getElementsByTagName("puzzle");


            for (int i = 0; i < nodes.getLength(); i++)
            {
                Node node = nodes.item(i);
                ArrayList<Block> temp = new ArrayList<Block>();

                Log.d("XMLParser", "Current Element: " + node.getNodeName());

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {

                    Element element = (Element) node;

                    Log.d("XMLParser", "id: " + element.getAttribute("id"));
                    Log.d("XMLParser", "level: " + element.getElementsByTagName("level").item(0).getTextContent());
                    Log.d("XMLParser", "length: " + element.getElementsByTagName("length").item(0).getTextContent());
                    Log.d("XMLParser", "setup: " + element.getElementsByTagName("setup").item(0).getTextContent());

                    String blockString = element.getElementsByTagName("setup").item(0).getTextContent();
                    String[] blockStringSplit = blockString.split(", ");

                    for (String block : blockStringSplit)
                    {
                        temp.add(stringsToBlocks(block));
                    }

                }
                blocks.add(temp);
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static Block stringsToBlocks(String s)
    {
        s = s.replace("(","");
        s = s.replace(")","");
        String[] sSplit = s.split(" ");

        int length = Integer.parseInt(sSplit[3]);
        boolean vertical = sSplit[0].equals("V");
        Point position = new Point(Integer.parseInt(sSplit[1]), Integer.parseInt(sSplit[2]));

        return new Block(length, vertical, position);
    }




}
