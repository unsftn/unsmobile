package rs.ac.uns.model;

/**
 * Created by t420 on 08.05.2017.
 */

public class Competitor {
    public String id;
    public String teamName;
    public String projectName;
    public String projectDesc;
    public String researchField;
    public int votesCount = -1;

    public Competitor(String id, String teamName, String researchField, String projectName, String projectDesc) {
        this.id = id;
        this.teamName = teamName;
        this.projectName = projectName;
        this.projectDesc = projectDesc;
        this.researchField = researchField;
        this.votesCount = -1;
    }

    @Override
    public String toString() {
        return "[" + this.votesCount + "] " + this.teamName + ", project: " + this.projectName + ", desc: " + this.projectDesc;
    }
}
