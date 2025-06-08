import React from "react";
import styles from "./Modal.module.css";

type ModalProps = {
  title: string;
  content: string;
  onConfirm: () => void;
  onCancel: () => void;
};

const Modal: React.FC<ModalProps> = ({ title, content, onConfirm, onCancel }) => {
  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modal}>
        <h2>{title}</h2>
        <p>{content}</p>
        <div className={styles.buttons}>
          <button onClick={onConfirm} className={styles.confirm}>
            Confirmar
          </button>
          <button onClick={onCancel} className={styles.cancel}>
            Cancelar
          </button>
        </div>
      </div>
    </div>
  );
};

export default Modal;