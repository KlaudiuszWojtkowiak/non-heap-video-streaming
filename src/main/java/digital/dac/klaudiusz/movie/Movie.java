package digital.dac.klaudiusz.movie;

import java.nio.ByteBuffer;

record Movie(ByteBuffer movieByteBuffer, Integer size) {
}
