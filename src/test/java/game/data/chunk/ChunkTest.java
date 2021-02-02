package game.data.chunk;

import game.Config;
import game.data.CoordinateDim2D;
import game.data.WorldManager;
import game.data.chunk.palette.BlockColors;
import game.data.dimension.Dimension;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import packets.builder.PacketBuilderAndParserTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ChunkTest extends PacketBuilderAndParserTest {
    @Override
    public void afterEach() {
    }

    @Test
    void x() throws IOException, ClassNotFoundException {
        // set up mock
        WorldManager mock = mock(WorldManager.class);
        when(mock.getBlockColors()).thenReturn(mock(BlockColors.class));

        WorldManager.setInstance(mock);

        Config.setProtocolVersion(751);

        // load chunk
        ObjectInputStream in = new ObjectInputStream(ChunkTest.class.getClassLoader().getResourceAsStream("chunkdata"));
        ChunkBinary chunkBinary = (ChunkBinary) in.readObject();

        CoordinateDim2D pos = new CoordinateDim2D(0, 0, Dimension.OVERWORLD);
        Chunk c = chunkBinary.toChunk(pos);

        builder = c.toPacket();

        assertThat(ChunkFactory.parseChunk(new ChunkParserPair(getParser(), pos.getDimension()), mock)).isEqualTo(c);
    }

}