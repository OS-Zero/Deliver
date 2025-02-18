import { createRoot } from 'react-dom/client';
import './styles/index.scss';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
if (import.meta.env.MODE === 'test') {
  import('./mock/index.ts');
}

createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);
