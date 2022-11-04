import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class AnimatedWorld extends World {

    private SwitchWorldAnimation joinAnimation; // when joining this world
    private SwitchWorldAnimation leaveAnimation; // when leaving this world

    public AnimatedWorld(int width, int height) {
        super(width, height, 1);
    }

    // adds default join animation, if one is active replaces
    public void addJoinAnimation() {
        if (this.joinAnimation != null) removeJoinAnimation();
        this.joinAnimation = new SwitchWorldAnimation(getWidth(), getHeight(), false);
        addObject(joinAnimation, getWidth()/2, getHeight()/2);
    }

    // adds join animation, if one is active replaces
    public void addJoinAnimation(SwitchWorldAnimation joinAnimation) {
        if (this.joinAnimation != null) removeJoinAnimation();
        this.joinAnimation = joinAnimation;
        addObject(joinAnimation, getWidth()/2, getHeight()/2);
    }

    // removes join animation, if active
    public void removeJoinAnimation() {
        if (joinAnimation == null) return;
        removeObject(joinAnimation);
        joinAnimation = null;
    }

    // adds default leave animation, if one is active replaces
    public void addLeaveAnimation() {
        if (this.leaveAnimation != null) removeLeaveAnimation();
        this.leaveAnimation = new SwitchWorldAnimation(getWidth(), getHeight(), true);
        addObject(leaveAnimation, getWidth()/2, getHeight()/2);
    }

    // adds leave animation, if one is active replaces
    public void addLeaveAnimation(SwitchWorldAnimation leaveAnimation) {
        if (this.leaveAnimation != null) removeLeaveAnimation();
        this.leaveAnimation = leaveAnimation;
        addObject(leaveAnimation, getWidth()/2, getHeight()/2);
    }

    // removes leave animation, if active
    public void removeLeaveAnimation() {
        if (leaveAnimation == null) return;
        removeObject(leaveAnimation);
        leaveAnimation = null;
    }

    // getters and setters
    public void setJoinAnimation(SwitchWorldAnimation joinAnimation) {
        this.joinAnimation = joinAnimation;
    }
    
    public SwitchWorldAnimation getJoinAnimation() {
        return this.joinAnimation;
    }
    
    public void setLeaveAnimation(SwitchWorldAnimation leaveAnimation) {
        this.leaveAnimation = leaveAnimation;
    }
    
    public SwitchWorldAnimation getLeaveAnimation() {
        return this.leaveAnimation;
    }

}
