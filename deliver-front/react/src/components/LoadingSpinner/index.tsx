import styles from './LoadingSpinner.module.css';

const LoadingSpinner: React.FC = () => {
  return (
    <div className={styles.spinnerContainer}>
      <div className={styles.spinner}></div>
      <p>加载中，请稍候...</p>
    </div>
  );
};

export default LoadingSpinner;
