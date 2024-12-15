import { useRoutes } from 'react-router-dom';
import routes from './router';

const App = () => {
  return useRoutes(routes);
}

export default App;