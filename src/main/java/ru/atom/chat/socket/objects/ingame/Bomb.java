package ru.atom.chat.socket.objects.ingame;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import ru.atom.chat.socket.objects.base.GameObject;
import ru.atom.chat.socket.objects.base.interfaces.Ticking;
import ru.atom.chat.socket.objects.base.util.Position;
import ru.atom.chat.socket.enums.ObjectType;

public class Bomb extends GameObject implements Ticking {

    private Pawn owner;
    @JsonIgnore
    private final long loopTime; // 5 seconds
    @JsonIgnore
    private long timeLeft;

    Bomb(Integer id, Position position, Pawn owner, long loopTime) {
        super(id, ObjectType.Bomb, position);
        this.owner = owner;
        this.loopTime = loopTime;
        this.timeLeft = loopTime;
    }

    @Override
    public void start() {
        timeLeft = loopTime;
    }

    @Override
    public void stop() {
        timeLeft = 0;
    }

    @Override
    public void tick(long ms) {
        if(isReady())
            log.warn("Error in GameObject with type " + getType() + ", ticking while object is ready");
        timeLeft -= ms;
    }

    @Override
    @JsonIgnore
    public boolean isReady() {
        return (timeLeft <= 0);
    }

    @Override
    @JsonIgnore
    public boolean isDestroyable() {
        return true;
    }

    @JsonIgnore
    public Pawn getOwner() {
        return owner;
    }
}
