package cz.eman.infinitescroll.model.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

@Table(name="cast_member")
public class CastMember extends Model {
    @Column
    @SerializedName("id")
    private Integer sid;
    @Column
    private String name;

    public Integer getSid() {
        return sid;
    }

    public String getName() {
        return name;
    }
}
