import { useState, useCallback, useRef } from 'react';
import { getVerificationCode } from '@/api/user';
import { validateEmail } from '@/views/Login/constant';
import { message } from 'antd';

interface VerifyState {
  verifyDisabled: boolean;
  verifyContent: string;
  remainingTime: number;
}

export const useVerify = () => {
  const [state, setState] = useState<VerifyState>({
    verifyDisabled: true,
    verifyContent: '获取验证码',
    remainingTime: 10
  });
  const intervalRef = useRef<NodeJS.Timeout | null>(null);

  const resetVerifyState = useCallback(() => {
    if (intervalRef.current) {
      clearInterval(intervalRef.current);
      intervalRef.current = null;
    }
    setState({
      verifyDisabled: true,
      verifyContent: '获取验证码',
      remainingTime: 10
    });
  }, []);

  const checkEmailFormat = useCallback(async (userEmail: string) => {
    try {
      await validateEmail(null, userEmail);
      setState((prev) => ({
        ...prev,
        verifyDisabled: false
      }));
      return true;
    } catch (e) {
      console.warn(e);
      setState((prev) => ({
        ...prev,
        verifyDisabled: true
      }));
      return false;
    }
  }, []);

  const handleVerify = useCallback(
    async (userEmail: string) => {
      try {
        if (intervalRef.current) {
          clearInterval(intervalRef.current);
        }
        await validateEmail(null, userEmail);
        setState((prev) => ({
          ...prev,
          verifyDisabled: true,
          verifyContent: '10s 后重新获取',
          remainingTime: 10
        }));
        await getVerificationCode({ userEmail });
        intervalRef.current = setInterval(() => {
          setState((prev) => {
            const newRemainingTime = prev.remainingTime - 1;
            if (newRemainingTime > 0) {
              return {
                ...prev,
                verifyContent: `${newRemainingTime}s 后重新获取`,
                remainingTime: newRemainingTime
              };
            } else {
              if (intervalRef.current) {
                clearInterval(intervalRef.current);
                intervalRef.current = null;
              }
              return {
                verifyDisabled: false,
                verifyContent: '获取验证码',
                remainingTime: 10
              };
            }
          });
        }, 1000);

        message.success('验证码已发送');
      } catch (error) {
        message.error(error instanceof Error ? error.message : '获取验证码失败');
        resetVerifyState();
      }

      return () => {
        if (intervalRef.current) {
          clearInterval(intervalRef.current);
        }
      };
    },
    [resetVerifyState]
  );

  return {
    verifyDisabled: state.verifyDisabled,
    verifyContent: state.verifyContent,
    handleVerify,
    checkEmailFormat
  };
};
