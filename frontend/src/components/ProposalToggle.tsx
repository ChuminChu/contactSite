"use client";

import React, { useState } from "react";
import { IoIosSend } from "react-icons/io";
import { IoMdMail } from "react-icons/io";
import Proposal from "@/components/Proposal";

type MailBoxType = "sent" | "received" | null;

export default function ProposalToggle(props: { token: string }) {
  const [visibleMailBox, setVisibleMailBox] = useState<MailBoxType>(null);

  const toggleProposal = (type: MailBoxType) => {
    setVisibleMailBox((prevType) => (prevType === type ? null : type));
  };

  return (
    <>
      <IoIosSend onClick={() => toggleProposal("sent")} />
      <IoMdMail onClick={() => toggleProposal("received")} />

      {visibleMailBox === "sent" && (
        <Proposal type="sent" token={props.token} />
      )}
      {visibleMailBox === "received" && (
        <Proposal type="received" token={props.token} />
      )}
    </>
  );
}
