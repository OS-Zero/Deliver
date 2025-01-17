export function omitProperty(obj: object, key: string) {
  return JSON.parse(
    JSON.stringify(
      obj,
      Object.keys(obj).filter((e) => e !== key)
    )
  );
}

export function omitProperties(obj: object, keys: string[]) {
  return JSON.parse(
    JSON.stringify(
      obj,
      Object.keys(obj).filter((e) => !keys.includes(e))
    )
  );
}

export function transformParams(params: any) {
  if (params?.current) {
    const { current, ...rest } = params;
    return {
      ...rest,
      currentPage: current || 1
    };
  }
  return params;
}
