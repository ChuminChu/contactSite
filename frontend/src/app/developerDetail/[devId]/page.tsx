import DevProfile from "@/components/DevProfile";
import "./devDetail.page.scss";
import { LuMessageCircleHeart } from "react-icons/lu";
import MessageButton from "@/components/MessageButton";

interface Developer {
  id: string;
  name: string;
  age: number;
  fieldName: string[];
  isLiked: boolean;
  likeCount: number;
  selfIntroduction: string;
  certificate: string;
}

const token =
  "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJQMSIsImlhdCI6MTczOTMyNDc5NCwiZXhwIjoxNzM5MzY3OTk0fQ.gv-gB9DB-ij94rkXWJtC4HWcxahEwRvtBUhxQ3NeWkU";
async function getDev(devId: string): Promise<Developer[]> {
  const res = await fetch(`http://localhost:8080/programmers/${devId}`, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    cache: "no-store",
  });
  if (!res.ok) throw new Error("Failed to fetch dev");
  return res.json();
}

export default async function DeveloperDetailPage({
  params,
}: {
  params: Promise<{ devId: string }>;
}) {
  const devId = (await params).devId;
  const myId = "";
  const developer = await getDev(devId);

  return (
    <div className="container">
      <div className="dev-detail">
        <DevProfile dev={developer} />
        {devId === myId || (
          <MessageButton devId={devId} token={token}></MessageButton>
        )}
      </div>
    </div>
  );
}
