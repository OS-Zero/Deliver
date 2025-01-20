import { Button, Result } from 'antd';

const NotFoundPage = () => {
  return (
    <Result
      status="404"
      title="404"
      subTitle="Sorry, the page you visited does not exist."
      extra={<Button type="link" href="/">返回首页</Button>}
    />
  );
};

export default NotFoundPage;
