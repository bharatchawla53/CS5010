package duration;

/**
 * Durations represented as hours, minutes, and seconds.
 */
public final class HmsDuration implements Duration {
  private final int hours;
  private final int minutes;
  private final int seconds;

  /**
   * Constructs a duration in terms of its length in hours, minutes, and
   * seconds.
   *
   * @param hours   the number of hours
   * @param minutes the number of minutes
   * @param seconds the number of seconds
   * @throws IllegalArgumentException if any argument is negative
   */
  public HmsDuration(int hours, int minutes, int seconds)
          throws IllegalArgumentException {
    if ((hours < 0) || (minutes < 0) || (seconds < 0)) {
      throw new IllegalArgumentException("Negative durations are not supported");
    }
    int h, m, s;
    h = hours;
    m = minutes;
    s = seconds;
    m = m + s / 60;
    s = s % 60;
    h = h + m / 60;
    m = m % 60;

    this.hours = h;
    this.minutes = m;
    this.seconds = s;
  }

  /**
   * Constructs a duration in terms of its length in seconds.
   *
   * @param inSeconds the number of seconds (non-negative)
   * @throws IllegalArgumentException {@code inSeconds} is negative
   */
  public HmsDuration(long inSeconds) {
    if (inSeconds < 0) {
      throw new IllegalArgumentException("must be non-negative");
    }

    seconds = (int) (inSeconds % 60);
    minutes = (int) (inSeconds / 60 % 60);
    hours = (int) (inSeconds / 3600);
  }

  @Override
  public long inSeconds() {
    return 3600 * hours + 60 * minutes + seconds;
  }

  @Override
  public String asHms() {
    return String.format("%d:%02d:%02d", hours, minutes, seconds);
  }

  @Override
  public Duration plus(Duration other) {
    long thisSeconds = this.inSeconds();
    long otherSeconds = other.inSeconds();
    long total = thisSeconds + otherSeconds;
    return new HmsDuration(total);
  }

  @Override
  public int compareTo(Duration that) {
    return Long.compare(this.inSeconds(), that.inSeconds());
  }


  @Override
  public boolean equals(Object o) {
    // Fast path for pointer equality:
    if (this == o) { //backward compatibility with default equals
      return true;
    }

    // If o isn't the right class then it can't be equal:
    if (!(o instanceof Duration)) {
      return false;
    }

    // The successful instanceof check means our cast will succeed:
    Duration that = (Duration) o;

    return this.inSeconds() == that.inSeconds();
  }

  @Override
  public int hashCode() {
    return Long.hashCode(this.inSeconds());
  }
}
