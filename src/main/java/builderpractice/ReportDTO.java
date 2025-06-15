package builderpractice;

public class ReportDTO {
    private String characterTvShow;
    private String personEmail;


    public static final class ReportDTOBuilder {
        private String characterTvShow;
        private String personEmail;

        private ReportDTOBuilder() {
        }

        public static ReportDTOBuilder builder() {
            return new ReportDTOBuilder();
        }

        public ReportDTOBuilder characterTvShow(String characterTvShow) {
            this.characterTvShow = characterTvShow;
            return this;
        }

        public ReportDTOBuilder personEmail(String personEmail) {
            this.personEmail = personEmail;
            return this;
        }

        public ReportDTO build() {
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.personEmail = this.personEmail;
            reportDTO.characterTvShow = this.characterTvShow;
            return reportDTO;
        }
    }
}
