export function omitProperty(obj: object, key: string) {
  return JSON.parse(
    JSON.stringify(
      obj,
      Object.keys(obj).filter((e) => e !== key)
    )
  );
}
