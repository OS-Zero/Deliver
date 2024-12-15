import { createRoot } from 'react-dom/client'
import '@/styles/index.module.scss'
import { BrowserRouter } from 'react-router-dom';
import App from './App.tsx'

createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <App/>
  </BrowserRouter>
)
