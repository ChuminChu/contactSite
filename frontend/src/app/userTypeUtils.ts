// app/utils/userTypeUtils.ts
import { cookies } from "next/headers";

let userType: string = "notLoggedIn"; // 기본값 설정

export function getUserType(): string {
  return userType;
}

export async function setUserType(): Promise<void> {
  const cookieStore = await cookies();
  const token = cookieStore.get("token")?.value;
  const storedUserType = cookieStore.get("userType")?.value;

  if (!token || !storedUserType) {
    userType = "notLoggedIn";
  } else {
    userType = storedUserType;
  }
}
