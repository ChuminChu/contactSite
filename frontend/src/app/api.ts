import { cookies } from "next/headers";

export async function signIn(
  userId: string,
  password: string,
  loginType: string,
): Promise<string> {
  const endpoint = loginType === "dev" ? "/programmer" : "/company";

  const newFormData = { userId, password };

  const response = await fetch(`http://localhost:8080/login${endpoint}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(newFormData),
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(`Failed to fetch company login: ${errorData.message}`);
  }

  const responseData = await response.json();
  return responseData.token; // 백엔드에서 받은 토큰 반환
}

export async function fetchUser(): Promise<string> {
  try {
    const cookieStore = await cookies();
    const token = cookieStore.get("token")?.value; // 쿠키에서 token을 가져옴
    // userType을 쿠키에서 가져옴
    const userType = cookieStore.get("userType")?.value;

    // token이 없으면 로그인되지 않은 상태로 판단
    if (!token || !userType) {
      return "notLoggedIn";
    }

    // userType (developer 또는 company) 반환
    return userType;
  } catch (error) {
    // 예외 처리
    if (error instanceof DOMException && error.name === "AbortError") {
      throw new Error("Request timed out");
    }
    if (error instanceof TypeError) {
      throw new Error("Network or parsing error");
    }
    throw error;
  }
}
