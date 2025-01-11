import { useRoutes } from 'react-router-dom';
import routes from './router';
import { GlobalProvider } from '@/context/GlobalContext.tsx';

const App = () => {
  return <GlobalProvider>{useRoutes(routes)}</GlobalProvider>;
};

export default App;
