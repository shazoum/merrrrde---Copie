package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class ContactGame implements Game {
	private Window window;
    private World world;
    
    private Entity ball;
    private ShapeGraphics ballGraphics;
    private float ballRadius=0.5f;
    
    private Entity block;
    private ImageGraphics blockGraphics;
    private float blockWidth=10;
    private float blockHeight=1;
    
    private BasicContactListener contactListener ;
    
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		// Store context
        this.window = window;
        // Successfully initiated
        world = new World();
        world.setGravity(new Vector(0.0f, -9.81f));

        
        EntityBuilder entityBuilder = world.createEntityBuilder() ;
        entityBuilder.setFixed(false) ;
	    entityBuilder.setPosition(new Vector(0.0f, 2.0f)) ;
	    ball=entityBuilder.build();
	    
	    PartBuilder partBuilder = ball.createPartBuilder() ;
	    partBuilder = ball.createPartBuilder() ;
	    Circle circle = new Circle(ballRadius,new Vector(0.0f,2.0f));
		partBuilder.setShape(circle) ;
		partBuilder.build();
		
		ballGraphics=new ShapeGraphics(circle, Color.BLUE,Color.BLUE,0.1f,1f,0) ;
		ballGraphics.setParent(ball);
		
		entityBuilder.setFixed(true) ;
	    entityBuilder.setPosition(new Vector(-5.0f, -1.0f)) ;
	    block=entityBuilder.build();
	    
	    partBuilder = block.createPartBuilder() ;
	    Polygon blockSolid = new Polygon(
	   	     new Vector(0.0f, 0.0f),
	   	     new Vector(blockWidth, 0.0f),
	   	     new Vector(blockWidth, blockHeight),
	   	     new Vector(0.0f, blockHeight)
	   	     ) ;
		partBuilder.setShape(blockSolid) ;
		partBuilder.build();
		
		blockGraphics= new ImageGraphics("stone.broken.4.png", blockWidth,blockHeight);
		blockGraphics.setParent(block);
		
        contactListener = new BasicContactListener() ;
        ball.addContactListener(contactListener) ;
		return true;
	}

	@Override
	public void update(float deltaTime) {
		world.update(deltaTime) ;
		
		ballGraphics.draw(window);
		blockGraphics.draw(window);
		
		window.setRelativeTransform(Transform.I.scaled(10.0f)) ;
		
		// contactListener is associated to ball
		// contactListener.getEntities() returns the list of entities in
		//collision with ball
		int numberOfCollisions = contactListener.getEntities().size() ;
		if (numberOfCollisions > 0){
		ballGraphics.setFillColor(Color.RED) ;
		}
		ballGraphics.draw(window) ;

	}

	@Override
	public void end() {
		

	}

}
