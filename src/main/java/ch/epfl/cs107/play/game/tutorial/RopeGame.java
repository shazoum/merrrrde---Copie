package ch.epfl.cs107.play.game.tutorial;


import java.awt.Color;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class RopeGame implements Game{
	// Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity block;
    private Entity ball;
    private ImageGraphics graphicsBlock ;
    private ShapeGraphics ballGraphics;
    private float sizeBlock=1.0f;
    public boolean begin(Window window, FileSystem fileSystem) {
		// Store context
        this.window = window;
        
        // Successfully initiated
        world = new World();
        world.setGravity(new Vector(0.0f, -9.81f));
        
        
        EntityBuilder entityBuilder = world.createEntityBuilder() ;
        
        entityBuilder.setFixed(true) ;
        

        entityBuilder.setPosition(new Vector(0.0f, 0.5f)) ;
        
        block = entityBuilder.build() ;        

	     PartBuilder partBuilder = block.createPartBuilder() ;

	     Polygon polygon = new Polygon(
	     new Vector(0.0f, 0.0f),
	     new Vector(sizeBlock, 0.0f),
	     new Vector(sizeBlock, sizeBlock),
	     new Vector(0.0f, sizeBlock)
	     ) ;
	     partBuilder.setShape(polygon) ;
	     partBuilder.build() ;

       graphicsBlock = new ImageGraphics("stone.broken.4.png", sizeBlock, sizeBlock) ;        
       graphicsBlock.setParent(block) ;
       
       EntityBuilder entityBuilder2 = world.createEntityBuilder() ;
       entityBuilder2.setFixed(false) ;
       entityBuilder2.setPosition(new Vector(0.6f, 4.0f)) ;
        
        ball = entityBuilder2.build() ;
        PartBuilder partBuilderCircle = ball.createPartBuilder() ;
	    Circle circle = new Circle(0.6f,new Vector(0.6f,4.0f));
	    partBuilderCircle.setShape(circle) ;
	    partBuilderCircle.build() ;
	    ballGraphics = new ShapeGraphics(circle , Color.BLUE, Color.RED,0.1f, 1.f, 0) ;
	    ballGraphics.setParent(ball) ;
	    
	    RopeConstraintBuilder ropeConstraintBuilder =
	    		world.createRopeConstraintBuilder() ;
	    		ropeConstraintBuilder.setFirstEntity(block) ;
	    		ropeConstraintBuilder.setFirstAnchor(new Vector(sizeBlock/2,sizeBlock/2)) ;
	    		ropeConstraintBuilder.setSecondEntity(ball) ;
	    		ropeConstraintBuilder.setSecondAnchor(Vector.ZERO) ;
	    		ropeConstraintBuilder.setMaxLength(6.0f) ;
	    		ropeConstraintBuilder.setInternalCollision(true) ;
	    		ropeConstraintBuilder.build() ;

		
		return true;
	}
	 // This event is called at each frame
    @Override
    public void update(float deltaTime) {
    	// Simulate physics
    	// Our body is fixed , though , nothing will move
    	world.update(deltaTime) ;
    	// We can render our scene now,
    	graphicsBlock.draw(window);
    	ballGraphics.draw(window) ;

    	
    	// we must place the camera where we want
    	// We will look at the origin (identity) and increase the view size a bit
    	window.setRelativeTransform(Transform.I.scaled(20.0f)) ;
    }
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
}
