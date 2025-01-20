import { useCallback, useRef } from 'react';

export function useDebounce<F extends (...args: any[]) => any>(fn: F, delay: number = 500) {
  const timeoutRef = useRef<NodeJS.Timeout | null>(null);

  return useCallback(
    (...args: Parameters<F>) => {
      if (timeoutRef.current) {
        clearTimeout(timeoutRef.current);
      }

      timeoutRef.current = setTimeout(() => {
        fn(...args);
        timeoutRef.current = null;
      }, delay);

      return () => {
        if (timeoutRef.current) {
          clearTimeout(timeoutRef.current);
        }
      };
    },
    [fn, delay]
  );
}
