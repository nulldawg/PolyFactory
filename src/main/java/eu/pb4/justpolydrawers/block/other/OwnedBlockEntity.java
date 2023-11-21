package eu.pb4.justpolydrawers.block.other;

import com.mojang.authlib.GameProfile;

public interface OwnedBlockEntity {
    GameProfile getOwner();
    void setOwner(GameProfile profile);
}
