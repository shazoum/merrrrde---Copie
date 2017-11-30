package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

/**
 * Simple game, to show basic the basic architecture
 */
public class HelloWorldGame implements Game {

    // Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity body;
    
    // graphical representation of the body
    private ImageGraphics graphics ;
    private ImageGraphics graphics2;
    
    // This event is raised when game has just started
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
        this.window = window;
        
       // TO BE COMPLETED
        // Successfully initiated
        world = new World();
        world.setGravity(new Vector(0.0f, -9.81f));
        
        // To create an object , you need to use a builder
        EntityBuilder entityBuilder = world.createEntityBuilder() ;
        
        // Make sure this does not move
        entityBuilder.setFixed(true) ;
        
        // This helps you define properties , like its initial location
        entityBuilder.setPosition(new Vector(0.f, 0.f)) ;
        
        // Once ready , the body can be built
        body = entityBuilder.build() ;
        
        graphics = new ImageGraphics("stone.broken.4.png", 1, 1) ;
        
        // Transparency can be chosen for each drawing (0.0 - transparent 1.0 - opaque)
        graphics.setAlpha(1.0f) ;
        
        // Additionally , you can choose a depth when drawing
        // Therefore , you could draw behind what you have already done
        graphics.setDepth(0.0f) ;
        
        graphics.setParent(body) ;
        graphics.setAnchor(new Vector(0.0f,1.f));
        graphics2 = new ImageGraphics("bow.png", 1, 1) ;
     // Transparency can be chosen for each drawing (0.0 - transparent ,  1.0 - opaque)
     graphics2.setAlpha(1.0f) ;
     // Additionally , you can choose a depth when drawing
     // Therefore , you could draw behind what you have already done
     graphics2.setDepth(0.0f) ;
     graphics2.setParent(body) ;
        
        
        return true;
    }

    // This event is called at each frame
    @Override
    public void update(float deltaTime) {
        
    	// Simulate physics
    	// Our body is fixed , though , nothing will move
    	world.update(deltaTime) ;
    	// We can render our scene now,
    	graphics.draw(window) ;
    	graphics2.draw(window);
    	// we must place the camera where we want
    	// We will look at the origin (identity) and increase the view size a bit
    	window.setRelativeTransform(Transform.I.scaled(5.0f)) ;

        // The actual rendering will be done now, by the program loop
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
    
}
