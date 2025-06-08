import React from "react";
import styles from "./Table.module.css";

type TableProps = {
  columns: Array<{ header: string; field: string }>;
  data: Array<Record<string, any>>;
};

const Table: React.FC<TableProps> = ({ columns, data }) => {
  return (
    <table className={styles.table}>
      <thead>
        <tr>
          {columns.map((col) => (
            <th key={col.field}>{col.header}</th>
          ))}
        </tr>
      </thead>
      <tbody>
        {data.map((row, index) => (
          <tr key={index}>
            {columns.map((col) => (
              <td key={col.field}>{row[col.field]}</td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default Table;