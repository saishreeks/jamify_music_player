
package jamify;

import java.io.Serializable;

public class  PlaylistNamePath implements Serializable {

    private String playListName;
    private String playListPath;

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }

    public String getPlayListPath() {
        return playListPath;
    }

    public void setPlayListPath(String playListPath) {
        this.playListPath = playListPath;
    }

    @Override
    public int hashCode()
    {
        int result = 17;



        result = 31 * result + playListName.hashCode();
        result = 31 * result + playListPath.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof PlaylistNamePath)) {
            return false;
        }

        PlaylistNamePath obj = (PlaylistNamePath) o;

        return obj.playListName.equals(playListName) &&
                obj.playListPath.equals(playListPath);


    }


}
