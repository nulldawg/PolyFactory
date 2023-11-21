package eu.pb4.justpolydrawers.data;

import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public record DataType(String id, Function<NbtCompound, DataContainer> nbtReader) {
    public static final Map<String, DataType> TYPES = new HashMap<>();

    public DataType {
        TYPES.put(id, this);
    }

    public static final DataType BOOL = new DataType("bool", BoolData::fromNbt);
    public static final DataType LONG = new DataType("long", LongData::fromNbt);
    public static final DataType STRING = new DataType("string", StringData::fromNbt);
    public static final DataType GAME_EVENT = new DataType("game_event", GameEventData::fromNbt);
    public static final DataType BLOCK_STATE = new DataType("block_state", BlockStateData::fromNbt);


}
