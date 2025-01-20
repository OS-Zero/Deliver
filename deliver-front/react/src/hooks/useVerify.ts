import { useState, useCallback, useEffect } from 'react';
import { validateEmail } from '@/views/Login/constant';
import { getVerificationCode } from '@/api/user';
import { message } from 'antd';

export const useVerify = (props: { email: string }) => {
  const { email } = props;
  const [verifyDisabled, setVerifyDisabled] = useState(true);
  const [countdown, setCountdown] = useState(0);

  const checkEmailFormat = useCallback(async () => {
    try {
      await validateEmail(null, email);
      if (countdown === 0) {
        setVerifyDisabled(false);
      }
    } catch {
      setVerifyDisabled(true);
    }
  }, [email, countdown]);

  const handleVerify = useCallback(async () => {
    try {
      await validateEmail(null, email);
      setVerifyDisabled(true);
      setCountdown(60);
      await getVerificationCode({ userEmail: email });
      message.success('验证码已发送');
      const interval = setInterval(() => {
        setCountdown((prev) => {
          if (prev <= 1) {
            clearInterval(interval);
            checkEmailFormat();
            return 0;
          }
          return prev - 1;
        });
      }, 1000);
    } catch {
      setVerifyDisabled(true);
    }
  }, [email, checkEmailFormat]);

  useEffect(() => {
    if (countdown === 0) {
      checkEmailFormat();
    }
  }, [email, countdown, checkEmailFormat]);

  return {
    verifyDisabled,
    verifyContent: countdown > 0 ? `${countdown}秒后重新获取` : '获取验证码',
    handleVerify,
    checkEmailFormat
  };
};
