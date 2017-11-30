package ch.epfl.cs107.play.game.tutorial;

import com.sun.glass.events.KeyEvent;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class ScaleGame implements Game {
	
	private Window window;
    private World world;
    
    private Entity block;
    private ImageGraphics blockGraphics;
    private float blockHeight=1f;
    private float blockWidth=10f;
    
    private Entity plank;
    private ImageGraphics plankGraphics;
    private float plankHeight=0.2f;
    private float plankWidth=5f;
    
    private Entity ball;
    private ImageGraphics ballGraphics;
    private float ballRadius=0.6f;
    
    public boolean begin(Window window, FileSystem fileSystem) {
		// Store context
        this.window = window;
        
        // Successfully initiated
        world = new World();
        world.setGravity(new Vector(0.0f, -9.81f));
        
        EntityBuilder entityBuilder = world.createEntityBuilder() ;
        entityBuilder.setFixed(true) ;
        entityBuilder.setPosition(new Vector(-5.0f, -1.0f)) ;
        block = entityBuilder.build() ;
        
        PartBuilder partBuilder = block.createPartBuilder() ;
	     Polygon blockSolid = new Polygon(
	     new Vector(0.0f, 0.0f),
	     new Vector(blockWidth, 0.0f),
	     new Vector(blockWidth,blockHeight),
	     new Vector(0.0f, blockHeight)
	     ) ;
	     partBuilder.setShape(blockSolid) ;
	     partBuilder.build() ;
	     
        blockGraphics = new ImageGraphics("stone.broken.4.png", blockWidth,blockHeight) ;        
        blockGraphics.setParent(block) ;
        
        
        entityBuilder.setFixed(false) ;
        entityBuilder.setPosition(new Vector(-2.5f, 0.8f)) ;
        plank = entityBuilder.build() ;
        partBuilder = plank.createPartBuilder() ;
        
	     Polygon plankSolid = new Polygon(
	     new Vector(0.0f, 0.0f),
	     new Vector(plankWidth, 0.0f),
	     new Vector(plankWidth, plankHeight),
	     new Vector(0.0f, plankHeight)
	     ) ;
	     partBuilder.setShape(plankSolid) ;
	     partBuilder.build() ;
	     
	     plankGraphics = new ImageGraphics("wood.3.png", plankWidth,plankHeight) ;        
	     plankGraphics.setParent(plank) ;
	     
	     
	     entityBuilder.setFixed(false) ;
	     entityBuilder.setPosition(new Vector(0.5f, 4f)) ;
	     ball=entityBuilder.build();
	     
	     partBuilder = ball.createPartBuilder() ;
	     Circle circle = new Circle(ballRadius);
		 partBuilder.setShape(circle) ;
		 partBuilder.build();
		 
		 ballGraphics=new ImageGraphics("explosive.11.png", ballRadius*2f,ballRadius*2f,new Vector(.5f,.5f)) ;
		 ballGraphics.setParent(ball);
		 
	     RevoluteConstraintBuilder revoluteConstraintBuilder =
	    		 world.createRevoluteConstraintBuilder() ;
	    		 
	    		 revoluteConstraintBuilder.setFirstEntity(block) ;
	    		 revoluteConstraintBuilder.setFirstAnchor(new Vector(blockWidth/2,
	    		 (blockHeight*7)/4)) ;
	    		 revoluteConstraintBuilder.setSecondEntity(plank) ;
	    		 revoluteConstraintBuilder.setSecondAnchor(new Vector(plankWidth/2,
	    		 plankHeight/2)) ;
	    		 revoluteConstraintBuilder.setInternalCollision(true) ;
	    		 revoluteConstraintBuilder.build() ;  
		return true;
	}
	 // This event is called at each frame
    @Override
    public void update(float deltaTime) {
    	
    	world.update(deltaTime) ;
    	
    	blockGraphics.draw(window);
    	plankGraphics.draw(window);
    	ballGraphics.draw(window);
    	
    	if (window.getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
    		ball.applyAngularForce(10.0f) ;
    	} else if (window.getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
    		ball.applyAngularForce(-10.0f) ;
    	}


    	window.setRelativeTransform(Transform.I.scaled(10.0f)) ;
    }
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
}
