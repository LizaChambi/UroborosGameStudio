package uroborosGameStudio.domain;

import javax.imageio.ImageIO;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Actor 
{
	public String name;
	public Point point; // X e Y
	public Dimension dimension; // ancho y alto
	static public BufferedImage image;

	public Actor(String name, String path, Integer x, Integer y, Integer width, Integer height) 
	{
		this.name = name;
		readImage(path);
		this.point = new Point(x,y);
		this.dimension = new Dimension (width, height);
	}
	
	private void readImage(String path)
	{
		try 
		{
			this.image = ImageIO.read(new File(path));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public BufferedImage getImage()
	{
		return this.image;
	}

}
