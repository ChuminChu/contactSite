import DevProfile from "@/components/DevProfile";
import Link from "next/link";
import { getAuthToken } from "@/app/login/actions";

interface Developer {
  id: string;
  userId: string;
  name: string;
  age: number;
  fieldName: string[];
  isLiked: boolean;
  likeCount: number;
  selfIntroduction: string;
  certificate: string;
}

export async function getDevSelf(): Promise<Developer[]> {
  const token = await getAuthToken(); // 서버 액션 호출

  const res = await fetch(`http://localhost:8080/programmers/my`, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    cache: "no-store",
  });
  if (!res.ok) throw new Error("Failed to fetch dev");
  return res.json();
}

export default async function DeveloperMyPage() {
  const developer = await getDevSelf();
  return (
    <div className="container">
      <DevProfile dev={developer} />
      <Link href={`/developerMyPage/devEditProfile`}>
        <button>수정</button>
      </Link>
    </div>
  );
}
