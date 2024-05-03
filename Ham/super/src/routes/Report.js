import ReportCheck from "../component/check/ReportCheck";
import ReportTable from "../component/table/ReportTable";
import styles from "./report.module.scss";

const Report = () => {
  return (
    <div className={styles.form}>
      <ReportCheck />
      <ReportTable />
    </div>
  );
};

export default Report;
