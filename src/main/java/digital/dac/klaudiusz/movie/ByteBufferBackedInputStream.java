package digital.dac.klaudiusz.movie;

import jakarta.annotation.Nonnull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Objects;

public class ByteBufferBackedInputStream extends InputStream {
    private ByteBuffer backendBuffer;

    public ByteBufferBackedInputStream(ByteBuffer backendBuffer) {
        Objects.requireNonNull(backendBuffer, "Given backend buffer can not be null!");
        this.backendBuffer = backendBuffer;
    }

    @Override
    public void close() {
        this.backendBuffer = null;
    }

    private void ensureStreamAvailable() throws IOException {
        if (this.backendBuffer == null) {
            throw new IOException("read on a closed InputStream!");
        }
    }

    @Override
    public int read() throws IOException {
        this.ensureStreamAvailable();
        return this.backendBuffer.hasRemaining() ? this.backendBuffer.get() & 0xFF : -1;
    }

    @Override
    public int read(@Nonnull byte[] buffer) throws IOException {
        return this.read(buffer, 0, buffer.length);
    }

    @Override
    public int read(@Nonnull byte[] buffer, int offset, int length) throws IOException {
        this.ensureStreamAvailable();
        Objects.requireNonNull(buffer, "Given buffer can not be null!");
        if (offset >= 0 && length >= 0 && length <= buffer.length - offset) {
            if (length == 0) {
                return 0;
            } else {
                int remainingSize = Math.min(this.backendBuffer.remaining(), length);
                if (remainingSize == 0) {
                    return -1;
                } else {
                    this.backendBuffer.get(buffer, offset, remainingSize);
                    return remainingSize;
                }
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public long skip(long n) throws IOException {
        this.ensureStreamAvailable();
        if (n <= 0L) {
            return 0L;
        }
        int length = (int) n;
        int remainingSize = Math.min(this.backendBuffer.remaining(), length);
        this.backendBuffer.position(this.backendBuffer.position() + remainingSize);
        return length;
    }

    @Override
    public int available() throws IOException {
        this.ensureStreamAvailable();
        return this.backendBuffer.remaining();
    }

    @SuppressWarnings("java:S1186")
    @Override
    public synchronized void mark(int var1) {
    }

    @Override
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    @Override
    public boolean markSupported() {
        return false;
    }
}